require talologgergraph.inc

SUMMARY = "taloLogger home automation data graphing"
DESCRIPTION = "\
    taloLoggerGraph is a graphing application that reads log data from a \
    database table and draws graphical plots on time axis including history \
    data and recent data to user provided image graphs \
"
HOMEPAGE = "http://http://olammi.iki.fi/sw/taloLoggerGraph/"
SECTION = "web"
LICENSE = "taloLogger"
LIC_FILES_CHKSUM = "file://LICENSE;md5=65aed37ab22b1a39b696185e5208bedf"

DEPENDS = "\
    talologger \
"

SRC_URI = "\
    http://olammi.iki.fi/sw/taloLoggerGraph/taloLoggerGraph_v12c.zip \
    file://taloLoggerGraph.conf \
    file://nginx-site.conf \
    file://php-pool.conf \
"
SRC_URI[md5sum] = "20c1fd2360ec484a6579effac93921db"
SRC_URI[sha256sum] = "2fbcf3c111c2441e4a4ed7de8b42a141345ea837cf12f0eeeabd6c4fd5546aa5"

S = "${WORKDIR}/taloLoggerGraph"

talologger_libdir = "${talologger_servicedir}/lib/taloLoggerGraph"
talologger_logdir = "${talologger_servicedir}/log"
    
do_install() {
    # Create a directory for the app in the /srv hierarchy
    install -d -g www-data -m755 ${D}${talologger_servicedir}

    # Create a lib directory and install PHP code into a subdirectory
    install -d -g talo -m755 ${D}${talologger_libdir}
    install -g talo -m644 *.php ${D}${talologger_libdir}
    install -g talo -m644 *.inc ${D}${talologger_libdir}

    # Create a directory for logs
    install -d -g talo -m775 ${D}${talologger_logdir}

    # Deploy static content into the nginx document root
    install -d -g www-data -m755 ${D}${talologger_wwwdatadir}
    install -g www-data -m644 ${WORKDIR}/taloLoggerGraph.conf ${D}${talologger_wwwdatadir}
    install -g www-data -m644 index.php ${D}${talologger_wwwdatadir}
    install -g www-data -m644 taloLoggerGraph_getchart.php ${D}${talologger_wwwdatadir}
    install -d -g www-data -m755 ${D}${talologger_wwwdatadir}/docs
    install -g www-data -m644 docs/*.png ${D}${talologger_wwwdatadir}/docs

    # Deploy nginx configuration as a config fragment
    install -d ${D}${sysconfdir}/nginx/sites-available
    install -d ${D}${sysconfdir}/nginx/sites-enabled
    install -m644 ${WORKDIR}/nginx-site.conf ${D}${sysconfdir}/nginx/sites-available/taloLogger
    ln -s ${sysconfdir}/nginx/sites-available/taloLogger \
        ${D}${sysconfdir}/nginx/sites-enabled/taloLogger

    # Deploy PHP-FPM pool configuration as a config fragment
    install -d -m755 ${D}${sysconfdir}/php-fpm.d
    install -m644 ${WORKDIR}/php-pool.conf \
        ${D}${sysconfdir}/php-fpm.d/taloLoggerGraph-pool.conf
    sed -i -e "s:TALOLOGGER_SYSTEM_TIMEZONE:${TALOLOGGER_SYSTEM_TIMEZONE}:" \
        ${D}${sysconfdir}/php-fpm.d/taloLoggerGraph-pool.conf
}

# TODO: talologger should be talologger-sqlite or similar
RDEPENDS_${PN} = "\
    talologger \
    nginx \
    php-fpm \
    d3js \
"

FILES_${PN} = "\
    ${talologger_servicedir} \
    ${sysconfdir}/nginx \
    ${sysconfdir}/php-fpm.d \
"
