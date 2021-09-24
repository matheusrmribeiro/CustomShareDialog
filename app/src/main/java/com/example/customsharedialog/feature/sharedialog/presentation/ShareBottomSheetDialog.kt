package com.example.customsharedialog.feature.sharedialog.presentation

import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.customsharedialog.R
import com.example.customsharedialog.databinding.BottomSheetShareDialogBinding
import com.example.customsharedialog.feature.sharedialog.domain.entities.DialogItemEntity
import com.example.customsharedialog.feature.sharedialog.presentation.adapter.BottomSheetRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class ShareBottomSheetDialog(private val textToShare: String) : BottomSheetDialogFragment() {

    companion object {
        private const val MORE_TAG = "MORE_TAG"
    }

    private lateinit var binding: BottomSheetShareDialogBinding
    private val availableApps: MutableList<DialogItemEntity> = ArrayList()
    private lateinit var sendIntent: Intent
    private val adapter = BottomSheetRecyclerAdapter { item -> onItemClicked(item) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetShareDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dialog = requireView().parent as View
        dialog.setBackgroundColor(Color.TRANSPARENT)
        val behavior = BottomSheetBehavior.from(dialog)
        behavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            skipCollapsed = true
            behavior.isDraggable = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        setupRecycler()
    }

    private fun setupRecycler() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 3)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun loadData() {
        val activity = requireActivity()
        sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textToShare)
            type = "text/plain"
        }

        val activities: List<ResolveInfo> = activity.packageManager.queryIntentActivities(
            sendIntent,
            0
        )

        for (info in activities) {
            availableApps.add(
                DialogItemEntity(
                    info.loadLabel(activity.packageManager).toString(),
                    info.loadIcon(activity.packageManager),
                    info.activityInfo.packageName
                )
            )
        }

        val moreDescription = resources.getString(R.string.share_more_options)
        val moreIcon = ResourcesCompat.getDrawable(
            resources,
            R.drawable.shape_action_more,
            null
        )
        moreIcon?.let { drawable ->
            availableApps.add(
                DialogItemEntity(
                    moreDescription,
                    drawable,
                    MORE_TAG
                )
            )
        }
        adapter.addItems(availableApps)
    }

    private fun onItemClicked(itemEntity: DialogItemEntity) {
        if (itemEntity.packageName == MORE_TAG) {
            val shareIntent = Intent.createChooser(
                sendIntent,
                resources.getString(R.string.share_title)
            )
            ContextCompat.startActivity(requireContext(), shareIntent, null)
        } else {
            sendIntent.setPackage(itemEntity.packageName)
            startActivity(sendIntent)
        }
        dismiss()
    }

}
