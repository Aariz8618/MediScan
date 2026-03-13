package com.aariz.mediscan

import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AppCompatActivity

/**
 * WindowInsetHelper
 *
 * Handles edge-to-edge display for a FLOATING CardView bottom nav
 * inside a CoordinatorLayout. Automatically adapts to:
 *   • Gesture navigation  → thin pill bar  (small inset)
 *   • 3-button navigation → tall button bar (larger inset)
 *
 * Call once from MainActivity.onCreate() AFTER setContentView():
 *
 *   WindowInsetHelper.apply(
 *       activity          = this,
 *       floatingNavCard   = binding.bnav,
 *       fragmentContainer = binding.fragmentContainer,
 *       baseNavMarginDp   = 14    // must match layout_marginBottom in XML
 *   )
 */
object WindowInsetHelper {

    fun apply(
        activity: AppCompatActivity,
        floatingNavCard: CardView,
        fragmentContainer: FrameLayout,
        baseNavMarginDp: Int = 14
    ) {
        // ── 1. Draw app content behind the system bars (edge-to-edge) ────
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)

        val density = activity.resources.displayMetrics.density

        // ── 2. Float the nav card above the system nav bar ───────────────
        // We take the base margin from XML (14dp) and add the live
        // system nav bar height on top — so the card always hovers
        // the correct distance above the bar regardless of nav mode.
        ViewCompat.setOnApplyWindowInsetsListener(floatingNavCard) { view, insets ->
            val navBarPx    = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            val baseMarginPx = (baseNavMarginDp * density).toInt()

            val params = view.layoutParams as CoordinatorLayout.LayoutParams
            params.bottomMargin = baseMarginPx + navBarPx
            view.layoutParams = params

            insets
        }

        // ── 3. Pad fragment container so content clears the floating card ─
        // Total clearance = card height (72dp) + base margin (14dp) + nav bar inset.
        // The hardcoded 72dp matches `android:layout_height="72dp"` in your XML.
        val navCardHeightPx = (72 * density).toInt()
        val baseMarginPx    = (baseNavMarginDp * density).toInt()

        ViewCompat.setOnApplyWindowInsetsListener(fragmentContainer) { view, insets ->
            val navBarPx = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            view.setPadding(
                view.paddingLeft,
                view.paddingTop,
                view.paddingRight,
                navCardHeightPx + baseMarginPx + navBarPx
            )
            insets
        }
    }
}