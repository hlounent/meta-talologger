DESCRIPTION = "Set up log configuration for talologger distro"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "\
    file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420 \
"

DEPENDS = "systemd"

PV = "0"

SRC_URI = "\
    file://00-journal.conf \
"

S = "${WORKDIR}"

inherit systemd

systemd_journald_confdir = "/usr/lib/systemd/journald.conf.d"

do_install() {
    install -d -m755 ${D}${systemd_journald_confdir}
    install -m644 00-journal.conf ${D}${systemd_journald_confdir}/
}

FILES_${PN} = "\
    ${systemd_journald_confdir} \
"
