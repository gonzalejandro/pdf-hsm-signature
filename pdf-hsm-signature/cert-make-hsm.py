#!/usr/bin/env python3
# coding: utf-8

import os
import sys
from endesive import hsm
import PyKCS11

cwd = os.getcwd()
os.environ['SOFTHSM2_CONF'] = 'softhsm2.conf'
if not os.path.exists(os.path.join(cwd, 'softhsm2.conf')):
    with open('softhsm2.conf', "wt") as conf:
        conf.write(
            f'''\
            log.level = DEBUG
            directories.tokendir = {cwd}/softhsm2/
            objectstore.backend = file
            slots.removable = false
            '''
        )

#
#!/bin/bash
#SOFTHSM2_CONF=softhsm2.conf
#softhsm2-util --label "endesive" --slot 1 --init-token --pin secret1 --so-pin secret2
#softhsm2-util --show-slots
#

dllpath = '/usr/lib/softhsm/libsofthsm2.so'


class HSM(hsm.HSM):
    def main(self):
        cakeyID = bytes((0x1,))
        rec = self.session.findObjects([(PyKCS11.CKA_CLASS, PyKCS11.CKO_PRIVATE), (PyKCS11.CKA_ID, cakeyID)])
        if len(rec) == 0:
            label = 'ca'
            self.gen_privkey(label, cakeyID)
            self.ca_gen(label, cakeyID, 'CA')

        keyID = bytes((0x66, 0x66, 0x90))
        rec = self.session.findObjects([(PyKCS11.CKA_CLASS, PyKCS11.CKO_PRIVATE_KEY), (PyKCS11.CKA_ID, keyID)])
        if len(rec) == 0:
            label = 'user 1'
            self.gen_privkey(label, keyID)
            self.ca_sign(keyID, label, 0x666690, "USER 1", 365, cakeyID)


def main():
    cls = HSM(dllpath)
    cls.create("endesieve", "secret1", "secret2")
    cls.login("endesieve", "secret1")
    try:
        cls.main()
    finally:
        cls.logout()


if __name__ == "__main__":
    main()
