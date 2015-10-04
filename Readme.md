FDTD-JAVA
=========
It is a Java program for modeling of electromagnetic field with FDTD algorightm.

Original authors: Stephen Kirkup, Irfan Mulla, Goodchild Ndou and Javad Yazdani.

Original source published at [Stephen Kirkup's site][main-site]. [Conference
report][report].

Adapted by Friedrich von Never to be executed with Gradle in modern environment.

Running
-------

For preprocessing of an `experiment.dat` file in the current directory:

    $ gradle run

For viewing the preprocessed file:

    $ gradle run -Ppost=true

License
-------
See `gpl.txt` for an original license.

[main-site]: http://www.kirkup.info/opensource/fdtd/intro.htm
[report]: http://www.wseas.us/e-library/conferences/2008/corfu/mnw/mnw59.pdf
