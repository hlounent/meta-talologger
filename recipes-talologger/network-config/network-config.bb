DESCRIPTION = "Set up networking for taloLogger distro"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "\
    file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420 \
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
