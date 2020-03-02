package com.tec.bufeo.capitan.Util.GlideCache;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tec.bufeo.capitan.R;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class IntegerVersionSignature implements Key {
    private int currentVersion;

    public IntegerVersionSignature(int currentVersion) {
        this.currentVersion = currentVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof IntegerVersionSignature) {
            IntegerVersionSignature other = (IntegerVersionSignature) o;
            return currentVersion == other.currentVersion;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return currentVersion;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest md) {
        md.update(ByteBuffer.allocate(Integer.SIZE).putInt(currentVersion).array());
    }


    public static class GlideOptions {
        public static RequestOptions LOGO_OPTION = new RequestOptions().placeholder(R.drawable.placeholder_image).centerCrop()
                .dontAnimate().dontTransform().diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        public static void UpdateSignatureOptions(int version){
            LOGO_OPTION = LOGO_OPTION.signature(new IntegerVersionSignature(version));
        }
    }



}

