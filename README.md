A web-based front-end for mplayer. This was my first [Wicket](http://wicket.apache.org/) application.
I wrote it couple of years ago, somewhere in 2009. It uses [Prevayler](http://prevayler.org/) to
store metainfo from music files. Metainfo is extracted using the [entagged](http://entagged.sourceforge.net/)
library.
    
Building
--------

Build with maven:

    mvn package
    
Running
-------

Deploy the previously built .war file into a servlet container such as Tomcat or Jetty. Alternatively, when using
the Eclipse IDE with the Maven plugin, run class MediaServer which uses the embedded Jetty library.
I have not investigated a way to run it from the command line.

Media files are played using the mplayer binary and it must be installed before using this software.

Usage
-----

Depending on the deploy method, navigate to path /app (MediaServer will start on port 11000,
so http://localhost:11000/app). Select Paths and add a path containing mp3 or flac files. Clicking
Update music database will recursively read metainfo from all suitable files in the path.

Then clicking Music or Artistist links will allow to browse the database and
play selected artists/albums/titles. 

License
-------

    The MIT License (MIT)
    Copyright (c) 2013 Raivo Laanemets
    
    Permission is hereby granted, free of charge, to any person obtaining a copy of this software
    and associated documentation files (the "Software"), to deal in the Software without restriction,
    including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
    and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
    subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all copies or substantial
    portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
    THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
