# Introduction

meta-talologger provides OpenEmbedded metadata / BitBake recipes for [taloLogger](http://olammi.iki.fi/sw/taloLogger/) home automation software suite. The metadata can be used to include taloLogger software in any distribution built based on and for any machine supported by Yocto.

Additionally meta-talologger provides a taloLogger image for Raspberry Pi, which directly allows for building a completely custom image for Raspberry Pi that contains taloLogger software.

meta-talologger is based on Yocto Project. See the [Yocto website](https://www.yoctoproject.org/) for more information on Yocto.


# Limitations

No attempts have been made to secure the installation / system! It is assumed that the system will be run on a private trusted LAN.

meta-talologger currently only packages taloLogger, taloLoggerGraph and their dependencies. dialEye is still to be added.

taloLogger and taloLoggerGraph use the default configuration files included in the taloLogger and taloLoggerGraph releases. Suitable configuration files need to be added to the final system to make it useful.

The system should have network connectivity for time synchronization using NTP.


# Dependencies

* meta-python: Python required by taloLogger
* meta-webserver: nginx for taloLoggerGraph
* meta-oe: PHP for taloLoggerGraph
* meta-raspberrypi: rpi-hwup-image for the Raspberry Pi image

Additionally some recipes have been imported from other layers in order to avoid creating a dependency.


# Building

Set up your Yocto build environment. See [Yocto Project Quick Start](http://www.yoctoproject.org/docs/latest/yocto-project-qs/yocto-project-qs.html) for more information.

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

* `DEFAULT_TIMEZONE`: the desired timezone. Will be used to set the system timezone (`/etc/localtime`) and the PHP `date.timezone` configuration option. An example: `Europe/Helsinki`. The default: `UTC`.
* `hostname_pn-base-files`: the desired hostname. See the [Yocto manual](http://www.yoctoproject.org/docs/latest/mega-manual/mega-manual.html#usingpoky-extend-customimage-image-name) for more information.

An example of items to be added to local.conf:

```
DISTRO ?= "talologgerdistro"
hostname_pn-base-files = "myhostname"
TALOLOGGER_SYSTEM_TIMEZONE = "Europe/Helsinki"
```

Build: in the `build` directory run `bitbake talologger-rpi` and `dd` the resulting image (`tmp/deploy/images/raspberrypi2/talologger-rpi-raspberrypi2.rpi-sdimg`) to an SD card.


# Design

In absence of proper documentation this section provides a short summary of the design of the meta-talologger layer.

The system uses `systemd` as its init mechanism. `systemd-networkd` is used for managing network interface(s), and `systemd-timesyncd` is used for time synchronization. `systemd-journald` is used to capture all logs instead of individual log files wherever possible. `systemd-journald` also naturally handles log rotation. Independent `systemd` targets are defined to group logging and visualization related services.

[File System Hierarchy Standard (FHS)](https://wiki.linuxfoundation.org/en/FHS) is followed in the filesystem layout as far as possible. The local service directory `/srv` is used for storing taloLogger and taloLoggerGraph related files including the www document root. Configuration files are stored in `/etc` as usual with the exception of `taloLoggerGraph.conf`, which needs to be accessible by PHP code. Data collected by taloLogger is stored in an sqlite database.

[nginx](http://nginx.org/) serves as the HTTP server, and `PHP-FPM` runs PHP code.

Configuration file directories are favored over large monolithic configuration files wherever possible, especially in `/etc`. This makes it easier for independent BitBake recipes to install their configuration fragments without needing to patch single monolithic configuration files.


# Maintainer

Hannu Lounento <hannu.lounento (at) iki.fi>
