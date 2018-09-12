require recipes-core/images/core-image-minimal.bb

IMAGE_FEATURES_append = " \
    ssh-server-dropbear \
"

IMAGE_INSTALL_append = " \
    log-config \
    network-config \
    talologger \
    talologgergraph \
    talologger-targets \
    time-config \
"

SYSTEMD_DEFAULT_TARGET = "all-talologger-services.target"
