package com.greentoad.turtlebody.docpicker.labels

import android.text.TextUtils
import com.greentoad.turtlebody.docpicker.R

/**
 * Created by niraj on 29-04-2019.
 */
object DocLabelConstants {


    object Docs {


        const val MASK = 0xF


        private val PDF_LIST = arrayListOf("pdf")
        const val PDF = 0x1


        private val DOC_LIST = arrayListOf("doc", "docx")
        const val DOC = 0x2

        private val PPT_LIST = arrayListOf("ppt", "pptx", "key", "odp", "pps")
        const val PPT = 0x3


        private val XLS_LIST = arrayListOf("xls", "xlsx", "ods", "xlr")
        const val XLS = 0x4

        private val TXT_LIST = arrayListOf("txt", "log")
        const val TXT = 0x5


        fun getId(ext: String): Int {
            return when {
                PDF_LIST.contains(ext) -> PDF
                DOC_LIST.contains(ext) -> DOC
                PPT_LIST.contains(ext) -> PPT
                XLS_LIST.contains(ext) -> XLS
                TXT_LIST.contains(ext) -> TXT
                else -> -1
            }
        }

        fun getExtLabel(pGroupId: Int): String {
            return when (pGroupId) {
                DOC -> "doc"
                PDF -> "pdf"
                PPT -> "ppt"
                TXT -> "txt"
                XLS -> "xls"
                else -> ""
            }
        }

        fun getColorRes(pGroupId: Int): Int {
            return when (pGroupId) {
                DOC -> R.color.tb_doc_doc
                PDF -> R.color.tb_doc_pdf
                PPT -> R.color.tb_doc_ppt
                TXT -> R.color.tb_doc_txt
                XLS -> R.color.tb_doc_xls
                else -> R.color.md_grey_600
            }
        }

        fun getColorLightRes(pGroupId: Int): Int {
            return when (pGroupId) {
                DOC -> R.color.tb_doc_light_doc
                PDF -> R.color.tb_doc_light_pdf
                PPT -> R.color.tb_doc_light_ppt
                TXT -> R.color.tb_doc_light_txt
                XLS -> R.color.tb_doc_light_xls
                else -> R.color.md_grey_600
            }
        }

    }

    object Audios {


        const val MASK = 0xF0

        private val AAC_LIST = arrayListOf("aac")
        const val AAC = 0x10

        private val MP3_LIST = arrayListOf("mp3", "mpa")
        const val MP3 = 0x20

        private val M4A_LIST = arrayListOf("m4a")
        const val M4A = 0x30

        private val WAV_LIST = arrayListOf("wav")
        const val WAV = 0x40

        private val LIST = arrayListOf("aif", "cda", "mid", "midi", "ogg", "wma", "wpl")
        const val AUDIO = 0xF0

        fun getId(ext: String): Int {
            return when {
                AAC_LIST.contains(ext) -> AAC
                M4A_LIST.contains(ext) -> M4A
                MP3_LIST.contains(ext) -> MP3
                WAV_LIST.contains(ext) -> WAV
                LIST.contains(ext) -> AUDIO
                else -> -1
            }
        }

        fun getExtLabel(pGroupId: Int): String {
            return when (pGroupId) {
                AAC -> "aac"
                MP3 -> "mp3"
                M4A -> "m4a"
                WAV -> "wav"
                else -> ""
            }
        }


        fun getColorRes(pGroupId: Int): Int {
            return when (pGroupId) {
                AAC -> R.color.tb_doc_audio_aac
                MP3 -> R.color.tb_doc_audio_mp3
                M4A -> R.color.tb_doc_audio_m4a
                WAV -> R.color.tb_doc_audio_wav
                else -> R.color.tb_doc_audio
            }
        }

        fun getColorLightRes(pGroupId: Int): Int {
            return when (pGroupId) {
                AAC -> R.color.tb_doc_light_audio_aac
                MP3 -> R.color.tb_doc_light_audio_mp3
                M4A -> R.color.tb_doc_light_audio_m4a
                WAV -> R.color.tb_doc_light_audio_wav
                else -> R.color.tb_doc_light_audio
            }
        }


    }

    object Images {


        const val MASK = 0xF00


        private val JPG_LIST = arrayListOf("jpg", "jpeg")
        const val JPG = 0x100

        private val PNG_LIST = arrayListOf("png")
        const val PNG = 0x200

        private val GIF_LIST = arrayListOf("gif")
        const val GIF = 0x300


        // IMAGE VECTOR

        private val AI_LIST = arrayListOf("ai")
        const val AI = 0x400


        private val SVG_LIST = arrayListOf("svg")
        const val SVG = 0x500


        private val PSD_LIST = arrayListOf("ps", "psd")
        const val PSD = 0x600


        private val LIST = arrayListOf("ico", "tif, tiff")
        const val IMAGE = 0xF00

        fun getId(ext: String): Int {
            return when {
                AI_LIST.contains(ext) -> AI
                GIF_LIST.contains(ext) -> GIF
                JPG_LIST.contains(ext) -> JPG
                PNG_LIST.contains(ext) -> PNG
                PSD_LIST.contains(ext) -> PSD
                SVG_LIST.contains(ext) -> SVG
                LIST.contains(ext) -> IMAGE
                else -> -1
            }
        }

        fun getExtLabel(pGroupId: Int): String {
            return when (pGroupId) {
                AI -> "ai"
                GIF -> "gif"
                JPG -> "jpg"
                PNG -> "png"
                PSD -> "psd"
                SVG -> "svg"
                else -> ""
            }
        }

        fun getColorRes(pGroupId: Int): Int {
            return when (pGroupId) {
                AI -> R.color.tb_doc_image_ai
                GIF -> R.color.tb_doc_image_gif
                JPG -> R.color.tb_doc_image_jpg
                PNG -> R.color.tb_doc_image_png
                PSD -> R.color.tb_doc_image_psd
                SVG  -> R.color.tb_doc_image_svg
                else -> R.color.tb_doc_image
            }
        }

        fun getColorLightRes(pGroupId: Int): Int {
            return when (pGroupId) {
                AI -> R.color.tb_doc_light_image_ai
                GIF -> R.color.tb_doc_light_image_gif
                JPG -> R.color.tb_doc_light_image_jpg
                PNG -> R.color.tb_doc_light_image_png
                PSD -> R.color.tb_doc_light_image_psd
                SVG  -> R.color.tb_doc_light_image_svg
                else -> R.color.tb_doc_light_image
            }
        }

    }


    object Videos {

        const val MASK = 0xF000

        private val GP3_LIST = arrayListOf("3g2", "3gp")
        const val GP3 = 0x1000

        private val AVI_LIST = arrayListOf("avi")
        const val AVI = 0x2000


        private val FLV_LIST = arrayListOf("flv", "swf")
        const val FLV = 0x3000

        private val MPG_LIST = arrayListOf("mp4", "mpg", "mpeg")
        const val MPG = 0x4000


        private val LIST = arrayListOf("h264", "mov", "rm", "swf", "m4v", "mkv", "wmv")
        const val VIDEO = 0xF000


        fun getId(ext: String): Int {
            return when {
                AVI_LIST.contains(ext) -> AVI
                FLV_LIST.contains(ext) -> FLV
                GP3_LIST.contains(ext) -> GP3
                MPG_LIST.contains(ext) -> MPG
                // else if (MKV_LIST.contains(ext)) return MKV
                LIST.contains(ext) -> VIDEO
                else -> -1
            }
        }

        fun getExtLabel(pGroupId: Int): String {
            return when (pGroupId) {
                AVI -> "ai"
                FLV -> "flv"
                GP3 -> "3gp"
                MPG -> "mpg"
                else -> ""
            }
        }

        fun getColorRes(pGroupId: Int): Int {
            return when (pGroupId) {
                AVI -> R.color.tb_doc_video_avi
                FLV -> R.color.tb_doc_video_flv
                GP3 -> R.color.tb_doc_video_3gp
                MPG -> R.color.tb_doc_video_mpg
                else -> R.color.tb_doc_video
            }
        }

        fun getColorLightRes(pGroupId: Int): Int {
            return when (pGroupId) {
                AVI -> R.color.tb_doc_light_video_avi
                FLV -> R.color.tb_doc_light_video_flv
                GP3 -> R.color.tb_doc_light_video_3gp
                MPG -> R.color.tb_doc_light_video_mpg
                else -> R.color.tb_doc_light_video
            }
        }

    }


    fun getDocLabelGroupId(ext: String): Int {
        var id = Docs.getId(ext)

        if (id != -1) {
            return id
        }

        id = Audios.getId(ext)
        if (id != -1) {
            return id
        }

        id = Images.getId(ext)
        if (id != -1) {
            return id
        }

        id = Videos.getId(ext)
        if (id != -1) {
            return id
        }
        return -1

    }

    fun getExtLabel(groupId: Int, pExt: String): String {
        var ext = ""
        if (groupId != -1) {
            when {
                (groupId and Docs.MASK) > 0 -> Docs.getExtLabel(groupId)
                (groupId and Audios.MASK) > 0 -> Audios.getExtLabel(groupId)
                (groupId and Images.MASK) > 0 -> Images.getExtLabel(groupId)
                (groupId and Videos.MASK) > 0 -> Videos.getExtLabel(groupId)
            }
        }
        if (TextUtils.isEmpty(ext)) {
            ext = if (pExt.length > 3) {
                pExt.substring(0, 3)
            } else {
                pExt

            }
        }
        return ext
    }

    fun getColorRes(groupId: Int): Int {
        if (groupId != -1) {
            return when {
                (groupId and Docs.MASK) > 0 -> Docs.getColorRes(groupId)
                (groupId and Audios.MASK) > 0 -> Audios.getColorRes(groupId)
                (groupId and Images.MASK) > 0 -> Images.getColorRes(groupId)
                (groupId and Videos.MASK) > 0 -> Videos.getColorRes(groupId)
                else -> R.color.md_grey_600
            }
        }
        return R.color.md_grey_600
    }

    fun getColorLightRes(groupId: Int): Int {
        if (groupId != -1) {
            return when {
                (groupId and Docs.MASK) > 0 -> Docs.getColorLightRes(groupId)
                (groupId and Audios.MASK) > 0 -> Audios.getColorLightRes(groupId)
                (groupId and Images.MASK) > 0 -> Images.getColorLightRes(groupId)
                (groupId and Videos.MASK) > 0 -> Videos.getColorLightRes(groupId)
                else ->  R.color.md_grey_50
            }
        }
        return R.color.md_grey_50
    }


}