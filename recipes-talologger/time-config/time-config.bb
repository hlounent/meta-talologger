DESCRIPTION = "Set up time configuration for taloLogger distro"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "\
    file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420 \
"

DEPENDS = "systemd"

PV = "0"

SRC_URI = "\
    file://00-ntp.conf \
"

S = "${WORKDIR}"

inherit systemd

systemd_timesyncd_confdir = "/usr/lib/systemd/timesyncd.conf.d"

TALOLOGGER_SYSTEM_TIMEZONE ?= "UTC"

do_install() {
    # Set the timezone
    install -d -m755 ${D}${sysconfdir}
    ln -s ${datadir}/zoneinfo/${TALOLOGGER_SYSTEM_TIMEZONE} ${D}${sysconfdir}/localtime

    # Set up systemd-timesyncd
    install -d -m755 ${D}${systemd_timesyncd_confdir}
    install -m644 00-ntp.conf ${D}${systemd_timesyncd_confdir}/
}

RDEPENDS_${PN} = "\
    tzdata \
    tzdata-europe \
"

FILES_${PN} = "\
    ${sysconfdir} \
    ${systemd_timesyncd_confdir} \
"

