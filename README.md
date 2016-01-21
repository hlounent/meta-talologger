# Introduction

meta-talologger provides OpenEmbedded metadata / BitBake recipes for [taloLogger](http://olammi.iki.fi/sw/taloLogger/) home automation software suite. Additionally it provides a taloLogger image for Raspberry Pi.

meta-talologger is based on Yocto Project. See the [Yocto website](https://www.yoctoproject.org/) for more information on Yocto.


# Limitations

No attempts have been made to secure the installation / system! It is assumed that the system will be run on a private trusted LAN.

meta-talologger currently only packages taloLogger, taloLoggerGraph and their dependencies. dialEye is still to be added.

taloLogger and taloLoggerGraph use the default configuration files included in the taloLogger and taloLoggerGraph releases. Suitable configuration files need to be added to the final system to make it useful.


# Dependencies

* meta-python: Python required by taloLogger
* meta-webserver: nginx for taloLoggerGraph
* meta-oe: PHP for taloLoggerGraph
* meta-raspberrypi: rpi-hwup-image for the Raspberry Pi image

Additionally some recipes have been imported from other layers in order to avoid creating a dependency.


# Building

Set up your Yocto build environment. See [Yocto Project Quick Start](http://www.yoctoproject.org/docs/2.0/yocto-project-qs/yocto-project-qs.html) for more information.

Add the dependencies to your `bblayers.conf`:

```
BBLAYERS ?= " \
  /path/to/poky/meta \
  /path/to/poky/meta-yocto \
  /path/to/poky/meta-yocto-bsp \
  /path/to/poky/meta-raspberrypi \
  /path/to/poky/meta-openembedded/meta-oe \
  /path/to/poky/meta-openembedded/meta-python \
  /path/to/poky/meta-openembedded/meta-webserver \
  /path/to/poky/meta-talologger \
  "
```

The generated image can / should be customized using the following variables e.g. in local.conf:

* `TALOLOGGER_SYSTEM_TIMEZONE`: the desired timezone. Will be used to set the system timezone (`/etc/localtime`) and the PHP `date.timezone` configuration option. An example: `Europe/Helsinki`. The default: `UTC`.
* `hostname_pn-base-files`: the desired hostname. See the [Yocto manual](http://www.yoctoproject.org/docs/latest/mega-manual/mega-manual.html#usingpoky-extend-customimage-image-name) for more information.

An example local.conf:

```
DISTRO ?= "talologgerdistro"
hostname_pn-base-files = "myhostname"
TALOLOGGER_SYSTEM_TIMEZONE = "Europe/Helsinki"
```

Build: in the `build` directory run `bitbake talologger-rpi` and `dd` the resulting image (`tmp/deploy/images/raspberrypi2/talologger-rpi-raspberrypi2.rpi-sdimg`) to an SD card.


# Maintainer

Hannu Lounento <hannu.lounento (at) iki.fi>
