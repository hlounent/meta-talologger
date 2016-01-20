require recipes-core/images/rpi-hwup-image.bb

IMAGE_FEATURES += "\
    ssh-server-dropbear \
"

IMAGE_INSTALL += "\
    log-config \
    network-config \
    talologger \
    time-config \
"
