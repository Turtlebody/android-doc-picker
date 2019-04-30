package com.greentoad.turtlebody.docpicker.labels

import java.io.Serializable


/**
 * Created by niraj on 29-04-2019.
 */

data class DocLabel(var colorRes: Int, var colorLightRes: Int, var text: String, var extType: String): Serializable {

}