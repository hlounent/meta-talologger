require recipes-core/images/rpi-hwup-image.bb

IMAGE_FEATURES += "\
    ssh-server-dropbear \
"

IMAGE_INSTALL += "\
    network-config \
    talologger \
"
