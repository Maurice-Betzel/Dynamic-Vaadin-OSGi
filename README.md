# Dynamic Vaadin on OSGi

Prerequisites: Java 8 and Maven 3.0.5

Download Apache Karaf 4.1.2, extract and startup

https://karaf.apache.org/download.html

Maven build ops4j-pax-web-6.0.7.1 available on this github and on the Karaf console execute:

    feature:repo-add mvn:org.ops4j.pax.web/pax-web-features/6.0.7.1/xml/features

And install the pax-http-whitboad extender:

    feature:install pax-http-whiteboard/6.0.7.1

Maven build this project root with mvn:install producing a Karaf Archive file (osgi-karaf-vaadin-kar-2.0.0.kar)

Drop this file into the deploy folder found under the Karaf main folder to install all needed bundles at once.

Open up a web browser and open http://localhost:8181/service to display the Vaadin SPA.

Start the dynamic bundle on the Karaf console to see the dynamic parts working.

Any new Ideas regarding this project are always welcome!

### Dynamic Vaadin on OSGi License

Copyright (C) 2016 Maurice Betzel
 
 Licensed either under the Apache License, Version 2.0, or (at your option)
 under the terms of the GNU General Public License as published by
 the Free Software Foundation (subject to the "Classpath" exception),
 either version 2, or any later version (collectively, the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
      http://www.apache.org/licenses/LICENSE-2.0
      http://www.gnu.org/licenses/
      http://www.gnu.org/software/classpath/license.html
 
 or as provided in the LICENSE.txt file that accompanied this code.
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

### Vaadin, fight for simplicity:

https://vaadin.com
https://github.com/vaadin
