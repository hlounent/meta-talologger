DESCRIPTION = "Install taloLogger specific systemd targets"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "\
    file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
"

DEPENDS = "systemd"

PV = "0"

SRC_URI = "\
    file://talologger-logging.target \
    file://talologger-visualization.target \
    file://all-talologger-services.target \
"

S = "${WORKDIR}"

inherit systemd

do_install() {
    install -d -m755 ${D}${systemd_system_unitdir}

    # Install the logging target
    install -m644 talologger-logging.target ${D}${systemd_system_unitdir}

    # Set up dependencies of logging target
    install -d -m755 ${D}${systemd_system_unitdir}/talologger-logging.target.wants
    ln -s ${systemd_system_unitdir}/multi-user.target ${D}${systemd_system_unitdir}/talologger-logging.target.wants/
    ln -s ${systemd_system_unitdir}/taloLogger.service ${D}${systemd_system_unitdir}/talologger-logging.target.wants/

    # Install the visualization target
    install -m644 talologger-visualization.target ${D}${systemd_system_unitdir}

    # Set up dependencies of the visualization target
    install -d -m755 ${D}${systemd_system_unitdir}/talologger-visualization.target.wants
    ln -s ${systemd_system_unitdir}/multi-user.target ${D}${systemd_system_unitdir}/talologger-visualization.target.wants/
    ln -s ${systemd_system_unitdir}/nginx.service ${D}${systemd_system_unitdir}/talologger-visualization.target.wants/
    ln -s ${systemd_system_unitdir}/php-fpm.service ${D}${systemd_system_unitdir}/talologger-visualization.target.wants/

    # Install the all-talologger-services target
    install -m644 all-talologger-services.target ${D}${systemd_system_unitdir}

    # Set up dependencies of the all-talologger-services target
    install -d -m755 ${D}${systemd_system_unitdir}/all-talologger-services.target.wants
    ln -s ${systemd_system_unitdir}/talologger-logging.target ${D}${systemd_system_unitdir}/all-talologger-services.target.wants/
    ln -s ${systemd_system_unitdir}/talologger-visualization.target ${D}${systemd_system_unitdir}/all-talologger-services.target.wants/
}

FILES_${PN} = "\
    ${systemd_system_unitdir} \
"
