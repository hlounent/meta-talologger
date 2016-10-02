SUMMARY = "taloLogger home automation data collector"
DESCRIPTION = "\
    taloLogger is an application developed to periodically retrieve house \
    automation data from multiple sources and to store the retrieved data to a data \
    store \
"
HOMEPAGE = "http://olammi.iki.fi/sw/taloLogger/"
SECTION = "python"
LICENSE = "taloLogger"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b3cf26fd49c5f0cbb300ab5539487456"

SRC_URI = "\
    http://olammi.iki.fi/sw/taloLogger/taloLogger_v17h.zip \
    file://taloLogger.service \
    file://taloLogger.sh \
"
SRC_URI[md5sum] = "c92217f59d3a2136b0a51ca56062a82f"
SRC_URI[sha256sum] = "ad7c472af4f0399364622fc6808445aa071225fcc828eb4ef28846138f994204"

S = "${WORKDIR}/taloLogger"

inherit useradd

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "--system --groups dialout talo"

do_install() {
    # TODO: Why cannot ExecStart=python taloLogger.py ...?
    #install -D -m555 taloLogger.py ${D}${bindir}/taloLogger

    install -D -m755 ${WORKDIR}/taloLogger.sh ${D}${bindir}/taloLogger
    TALOLOGGER_DATA_DIR=${D}${datadir}/taloLogger
    install -d ${TALOLOGGER_DATA_DIR}
    install -m644 taloLogger.py ${TALOLOGGER_DATA_DIR}

    cp -r modules ${TALOLOGGER_DATA_DIR}/
    chown -R root.talo ${TALOLOGGER_DATA_DIR}
    chmod -R 644 ${TALOLOGGER_DATA_DIR}
    find ${TALOLOGGER_DATA_DIR} -type d | xargs chmod a+X

    install -D -m644 ${WORKDIR}/taloLogger.service ${D}${systemd_system_unitdir}/taloLogger.service
    install -D -m 644 taloLogger.conf ${D}${sysconfdir}/taloLogger/taloLogger.conf
    install -d -g talo -m770 ${D}${localstatedir}/log/taloLogger
    install -d -g talo -m770 ${D}${servicedir}/taloLogger/db
}

# TODO: Clean up python dependencies: replace python-modules with the real
# dependencies
RDEPENDS_${PN} = "\
    python \
    python-core \
    python-re \
    python-shell \
    python-stringold \
    python-subprocess \
    python-lang \
    python-netclient \
    python-xml \
    python-ctypes \
    python-json \
    python-html \
    python-pyserial \
    python-pysqlite \
    python-modules \
"

FILES_${PN} = "\
    ${bindir} \
    ${datadir}/taloLogger \
    ${systemd_system_unitdir} \
    ${sysconfdir} \
    ${localstatedir}/log \
    ${localstatedir}/volatile/log \
    ${servicedir}/taloLogger/db \
"
