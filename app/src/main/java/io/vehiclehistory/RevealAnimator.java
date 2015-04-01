package io.vehiclehistory;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.ViewAnimationUtils;

public class RevealAnimator {

    private static final int DURATION = 600;

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public Animator buildRevealAnimation(View startView, View revealedView) {
        int[] startViewLocation = new int[2];
        startView.getLocationOnScreen(startViewLocation);

        int[] revealedViewLocation = new int[2];
        revealedView.getLocationOnScreen(revealedViewLocation);

        int cx = (startViewLocation[0] + (startView.getWidth() / 2));
        int cy = (startViewLocation[1] + (startView.getHeight() / 2) - revealedViewLocation[1]);

        int finalRadius = Math.max(revealedView.getWidth(), revealedView.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(revealedView, cx, cy, 0, finalRadius);

        anim.setDuration(DURATION);

        return anim;
    }
}
