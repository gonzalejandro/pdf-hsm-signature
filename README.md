# pdf-hsm-signature
A simple pdf signing module using an HSM.

The pdf-hsm-signature project is being developed to be used by the CLCERT Random Beacon to sign the every minute generated PDF documents. It contains two implementations (using Python and Java) of scripts used to sign and verify PDF documents using a Hardware Security Module (HSM). 


## Python Implementation

The first implementation was written in Python 3 using the endesive library to handle, sign and verify the signature of PDF documents. This library depends on as1ncrypto to make cryptographical operations.

[Here]() you will find a step by step tutorial to sign and verify a PDF using an HSM using the Python implementation.


## Java Implementation

The second implementation was written in Java using the Apache PDFBox to sign and verify the PDF documents and the Bouncy Castle Crypto API to make the cryptographical operations.

[Here]() you will find a step by step tutorial to sign and verify a PDF using an HSM using the Java implementation.
