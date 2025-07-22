# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-CiroDonnarumma89.git;protocol=ssh;branch=main \
#		   file://aesdchar-start-stop"

SRC_URI = "git:///home/ciro/Projects/Coursera/EmbeddedLinux/assignment-3-and-later-CiroDonnarumma89;protocol=file;branch=main \
		   file://aesdchar-start-stop"

PV = "1.0+git${SRCPV}"
SRCREV = "75b28f6ef445e10d7435e6ca5afb4f299092f31a"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"


do_install () {
    module_do_install

    install -d ${D}${sysconfdir}/init.d
    install -d ${D}$bindir

    install -m 0755 ${WORKDIR}/aesdchar-start-stop ${D}${sysconfdir}/init.d/aesdchar-start-stop
    install -m 0755 ${S}/aesdchar_load ${D}$bindir
    install -m 0755 ${S}/aesdchar_unload ${D}$bindir

}

FILES:${PN} += "${sysconfdir}/init.d/aesdchar-start-stop"
FILES:${PN} += "${bindir}/aesdchar_load"
FILES:${PN} += "${bindir}/aesdchar_unload"

INITSCRIPT_NAME = "aesdchar-start-stop"
INITSCRIPT_PARAMS = "defaults"

inherit update-rc.d