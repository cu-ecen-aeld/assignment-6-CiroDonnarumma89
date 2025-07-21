# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-CiroDonnarumma89.git;protocol=ssh;branch=main \
           file://0001-Update-module-load.patch \
           file://0002-Build-only-misc.patch \
           file://misc-start-stop \
           "


PV = "1.0+git${SRCPV}"
SRCREV = "57f9afd44c95dad11c71b94986067fdf6e955006"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_install() {
    module_do_install  # installa il modulo come faceva la versione automatica

    install -d ${D}${sysconfdir}/init.d
    install -d ${D}$bindir

    install -m 0755 ${WORKDIR}/misc-start-stop ${D}${sysconfdir}/init.d/
    install -m 0755 ${S}/misc-modules/module_load ${D}$bindir
    install -m 0755 ${S}/misc-modules/module_unload ${D}$bindir
}

FILES:${PN} += "${sysconfdir}/init.d/misc-start-stop"
FILES:${PN} += "${bindir}/module_load"
FILES:${PN} += "${bindir}/module_unload"

INITSCRIPT_NAME = "misc-start-stop"
INITSCRIPT_PARAMS = "defaults"

inherit update-rc.d