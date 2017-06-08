# TODO: Make d3js recipe independent of talologger recipe. Need to install it as
# a "private" library in the talologger lib dir in /srv hierarchy. How to do
# this without a dependency on talologger?

require talologgergraph.inc
require recipes-talologger/talologger/talo.inc

SUMMARY = "D3.js is a JavaScript library for manipulating documents based on data"
DESCRIPTION = "\
    D3.js is a JavaScript library for manipulating documents based on data. D3 \
    helps you bring data to life using HTML, SVG, and CSS. D3’s emphasis on web \
    standards gives you the full capabilities of modern browsers without tying \
    yourself to a proprietary framework, combining powerful visualization \
    components and a data-driven approach to DOM manipulation. \
"
HOMEPAGE = "http://http://d3js.org/"
SECTION = "javascript"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9a43e3ae9eeb6821b99b41158d0b08cd"

SRC_URI = "\
    https://github.com/mbostock/d3/releases/download/v${PV}/d3.zip \
"
SRC_URI[md5sum] = "60d619b60231c3eff17e92a0fba2a409"
SRC_URI[sha256sum] = "03e8402565045cea673588bb177a94fbc55d8f7be914868320073a20542f5940"

DEPENDS = "\
    talologgergraph \
"

S = "${WORKDIR}"

d3js_libdir = "${talologger_wwwdatadir}/js"

do_install() {
    install -d -g talo -m755 ${D}${talologger_servicedir}

    install -d -g talo -m755 ${D}${d3js_libdir}
    # talologgergraph expects the filename d3.v3.js
    install -g talo -m644 d3.js ${D}${d3js_libdir}/d3.v3.js
    install -g talo -m644 d3.min.js ${D}${d3js_libdir}
}

FILES_${PN} = "\
    ${d3js_libdir} \
"
