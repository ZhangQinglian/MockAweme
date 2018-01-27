package com.zqlite.android.mockaweme.fragment.camera

import android.Manifest
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.tencent.rtmp.ui.TXCloudVideoView
import com.tencent.ugc.TXRecordCommon
import com.tencent.ugc.TXUGCRecord
import com.zqlite.android.mockaweme.R
import com.zqlite.android.mockaweme.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_camera.*

/**
 * Created by scott on 2018/1/12.
 */
class CameraFragment : BaseFragment() {

    private var mTXCameraRecord: TXUGCRecord? = null
    private var mVideoView: TXCloudVideoView? = null
    private var mCallback:Callback?=null
    private var mIsFront = true
    interface Callback{
        fun onBackPress()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_camera
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mTXCameraRecord = TXUGCRecord.getInstance(context)
        mTXCameraRecord!!.setVideoRecordListener(object : TXRecordCommon.ITXVideoRecordListener {
            override fun onRecordEvent(p0: Int, p1: Bundle?) {
            }

            override fun onRecordComplete(p0: TXRecordCommon.TXRecordResult?) {
            }

            override fun onRecordProgress(p0: Long) {
            }

        })
        mVideoView = view.findViewById(R.id.video_view)
        Log.d("scott","camera fragment onViewCreated")
        back.setOnClickListener { mCallback?.onBackPress() }
        les_switch.setOnClickListener {
            mIsFront = !mIsFront
            if(mIsFront){
                les_switch.setImageResource(R.drawable.tu)
            }else{
                les_switch.setImageResource(R.drawable.a95)
            }
            mTXCameraRecord!!.switchCamera(mIsFront)
        }

        val adapter = FilterAdapter()
        val layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        filter_list.adapter = adapter
        filter_list.layoutManager = layoutManager
    }

    fun setCallback(callback: Callback){
        mCallback = callback
    }

    fun startPreview() {
        Log.d("scott", "start preview ")
        checkPermission()
    }

    fun stopPreview(){
        mTXCameraRecord!!.stopCameraPreview()
    }
    private fun checkPermission() {
        Log.d("scott", "check permission")
        Dexter.withActivity(activity).
                withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if(report?.grantedPermissionResponses?.size == 3){
                    startPreviewInner()
                }
            }

            override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
            }

        }).check()
    }

    private fun startPreviewInner(){
        val param = TXRecordCommon.TXUGCSimpleConfig()
        param.videoQuality = TXRecordCommon.VIDEO_QUALITY_MEDIUM        // 540p
        param.isFront = true           //是否前置摄像头，使用
        param.minDuration = 5000    //视频录制的最小时长ms
        param.maxDuration = 60000    //视频录制的最大时长ms
        mTXCameraRecord!!.setBeautyDepth(5,5,5,5)
        mTXCameraRecord!!.startCameraSimplePreview(param, mVideoView!!)
    }

    inner class FilterAdapter : RecyclerView.Adapter<FilterItemHolder>(){

        private val filterDrawableIds = arrayListOf(
                R.drawable.a5d,
                R.drawable.a5e,
                R.drawable.a5f,
                R.drawable.a5g,
                R.drawable.a5i,
                R.drawable.a5j,
                R.drawable.a5k,
                R.drawable.a5l,
                R.drawable.a5q,
                R.drawable.a5p,
                R.drawable.a5s,
                R.drawable.a5r,
                R.drawable.a5v,
                R.drawable.a5x,
                R.drawable.a5h,
                R.drawable.a5m,
                R.drawable.a5n,
                R.drawable.a5o,
                R.drawable.a5t,
                R.drawable.a5u,
                R.drawable.a5w,
                R.drawable.a5y,
                R.drawable.a5z)
        override fun onBindViewHolder(holder: FilterItemHolder?, position: Int) {
            holder!!.update(filterDrawableIds[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FilterItemHolder {
            val view =  LayoutInflater.from(context).inflate(R.layout.listitem_filter_item,parent,false)
            return FilterItemHolder(view)
        }

        override fun getItemCount(): Int {
            return filterDrawableIds.size
        }

    }

    inner class FilterItemHolder(val view:View) : RecyclerView.ViewHolder(view){
        private var thumb:ImageView = view.findViewById(R.id.thumb)

        fun update(drawableId:Int){
            thumb.setImageResource(drawableId)
        }
    }

    companion object Instance {
        fun getInstance(args: Bundle?): CameraFragment {
            val fragment = CameraFragment()
            if (args != null) {
                fragment.arguments = args
            }
            return fragment
        }
    }
}