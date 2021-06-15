package xyz.sachil.essence.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.sachil.essence.R
import xyz.sachil.essence.util.clearCache
import xyz.sachil.essence.databinding.FragmentSettingsBinding
import xyz.sachil.essence.util.getCacheSize

class SettingsFragment : Fragment() {
    companion object {
        private const val TAG = "SettingsFragment"
    }

    private lateinit var viewBinding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.CacheSize.text = requireContext().getCacheSize()
        viewBinding.clearCache.setOnClickListener {
            showClearCacheDialog()
        }
    }

    private fun showClearCacheDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_title)
            .setMessage(R.string.dialog_clear_cache_tips)
            .setPositiveButton(R.string.dialog_positive_button) { _, _ ->

                lifecycleScope.launch (Dispatchers.Main){
                    withContext(Dispatchers.IO){
                        requireContext().clearCache()
                    }
                    viewBinding.CacheSize.text = requireContext().getCacheSize()
                }
            }.setNegativeButton(R.string.dialog_negative_button, null)
            .show()
    }
}