package xyz.sachil.essence.fragment.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.sachil.essence.BuildConfig
import xyz.sachil.essence.R
import xyz.sachil.essence.databinding.DialogAboutBinding
import xyz.sachil.essence.databinding.FragmentSettingsBinding
import xyz.sachil.essence.util.*

class SettingsFragment : Fragment() {
    companion object {
        private const val TAG = "SettingsFragment"
    }

    private lateinit var viewBinding: FragmentSettingsBinding
    private lateinit var aboutDialogViewBinding: DialogAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.defaultNightMode.text =
            convertDefaultNightModeToString(requireContext().getDefaultNightMode())
        viewBinding.clearCache.setOnClickListener {
            showClearCacheDialog()
        }

        viewBinding.darkTheme.setOnClickListener {
            showNightModeDialog()
        }
        viewBinding.about.setOnClickListener {
            showAboutDialog()
        }
    }

    override fun onResume() {
        super.onResume()
        viewBinding.cacheSize.text = requireContext().getCacheSize()
    }


    private fun showClearCacheDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_clear_cache_title)
            .setMessage(R.string.dialog_clear_cache_tips)
            .setPositiveButton(R.string.dialog_positive_button) { _, _ ->

                lifecycleScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO) {
                        requireContext().clearCache()
                    }
                    viewBinding.cacheSize.text = requireContext().getCacheSize()
                }
            }.setNegativeButton(R.string.dialog_negative_button, null)
            .show()
    }

    private fun showNightModeDialog() {
        var defaultNightMode: Int = requireContext().getDefaultNightMode()
        val checkedItemIndex = when (defaultNightMode) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> 0
            AppCompatDelegate.MODE_NIGHT_NO -> 1
            else -> 2
        }
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.settings_dark_theme)
            .setSingleChoiceItems(R.array.dialog_dark_theme, checkedItemIndex) { _, which ->
                when (which) {
                    0 -> defaultNightMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    1 -> defaultNightMode = AppCompatDelegate.MODE_NIGHT_NO
                    2 -> defaultNightMode = AppCompatDelegate.MODE_NIGHT_YES
                }
            }.setPositiveButton(R.string.dialog_positive_button) { _, _ ->
                AppCompatDelegate.setDefaultNightMode(defaultNightMode)
                requireContext().setDefaultNightMode(defaultNightMode)
                viewBinding.defaultNightMode.text =
                    convertDefaultNightModeToString(defaultNightMode)
            }.setNegativeButton(R.string.dialog_negative_button, null)
            .show()
    }

    private fun showAboutDialog() {
        aboutDialogViewBinding = DialogAboutBinding.inflate(LayoutInflater.from(requireContext()))
        aboutDialogViewBinding.version.text = "Version: ${BuildConfig.VERSION_NAME}"
        aboutDialogViewBinding.url.text = Utils.PROJECT_URL

        AlertDialog.Builder(requireContext())
            .setView(aboutDialogViewBinding.root)
            .show()
    }

    private fun convertDefaultNightModeToString(defaultNightMode: Int): String {
        val nightModeArrays = resources.getStringArray(R.array.dialog_dark_theme)
        val index = when (defaultNightMode) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> 0
            AppCompatDelegate.MODE_NIGHT_NO -> 1
            else -> 2
        }
        return nightModeArrays[index]
    }
}