DESCRIPTION = "Set up log configuration for talologger distro"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "\
    file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
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
