package me.htookyaw.movieapp

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideMovie : AppGlideModule() {

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}
