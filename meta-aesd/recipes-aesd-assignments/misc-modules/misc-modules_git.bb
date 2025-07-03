# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-CiroDonnarumma89.git;protocol=ssh;branch=main \
           file://0001-Update-module-load.patch \
           file://0002-Build-only-misc.patch \
           "


PV = "1.0+git${SRCPV}"
SRCREV = "57f9afd44c95dad11c71b94986067fdf6e955006"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_install() {
    module_do_install  # installa il modulo come faceva la versione automatica
}