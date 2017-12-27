DESCRIPTION = "Set up networking for taloLogger distro"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "\
    file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
"

DEPENDS = "systemd"

PV = "0"

SRC_URI = "\
    file://00-dhcp.network \
"

S = "${WORKDIR}"

inherit systemd

systemd_network_unitdir = "/usr/lib/systemd/network"

do_install() {
    install -d -m755 ${D}${systemd_network_unitdir}
    install -m644 00-dhcp.network ${D}${systemd_network_unitdir}/
}

FILES_${PN} = "\
    ${systemd_network_unitdir} \
"
