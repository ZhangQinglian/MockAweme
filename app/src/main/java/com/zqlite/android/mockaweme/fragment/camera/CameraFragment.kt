package com.zqlite.android.mockaweme.fragment.camera

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.VideoView
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.tencent.rtmp.ui.TXCloudVideoView
import com.tencent.ugc.TXRecordCommon
import com.tencent.ugc.TXUGCRecord
import com.zqlite.android.mockaweme.R
import com.zqlite.android.mockaweme.base.BaseFragment

/**
 * Created by scott on 2018/1/12.
 */
class CameraFragment : BaseFragment() {

    private var mTXCameraRecord: TXUGCRecord? = null
    private var mVideoView: TXCloudVideoView? = null
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
                //Log.d("scott", "granted permissions : " + report?.grantedPermissionResponses)
                //Log.d("scott", "denied permissions : " + report?.deniedPermissionResponses)
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