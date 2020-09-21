package com.rdm.blockchainchart

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProviders
import android.graphics.DashPathEffect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.rdm.blockchainchart.model.BlockchainChartUpdate
import com.rdm.blockchainchart.model.BlockchainTransactionsResponse
import com.rdm.blockchainchart.model.PointValue
import com.rdm.blockchainchart.viewmodels.BlockchainTransactionViewModel
import kotlinx.android.synthetic.main.chart_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class BlockchainTransactionFragment : Fragment(), SeekBar.OnSeekBarChangeListener,
    BlockchainChartUpdate {
    private var blockchainTransactionViewModel: BlockchainTransactionViewModel? = null
    private var blockchainTransactionsResponse: LiveData<BlockchainTransactionsResponse?>? = null
    private val entries: ArrayList<Entry> = ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        blockchainTransactionViewModel = ViewModelProviders.of(this).get(BlockchainTransactionViewModel::class.java)
        blockchainTransactionViewModel?.blockchainChartUpdate = this
      return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seekBarWeek.setOnSeekBarChangeListener(this)
        seekBarHours.setOnSeekBarChangeListener(this)
        blockchainTransactionsResponse = blockchainTransactionViewModel?.getBlockchainTransactionsResponseLiveData()
    }

    private fun setupChart(blockchainTransactionsResponse: BlockchainTransactionsResponse?) {

        var lineDataSet = LineDataSet(blockchainTransactionsResponse?.values?.let {
            dataValues(
                it
            )

        }, blockchainTransactionsResponse?.period)

        var dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(lineDataSet)

        var data = LineData(dataSets)
        lineDataSet.setDrawValues(false)
        lineDataSet.setDrawFilled(true)
        lineDataSet.lineWidth = 3f
        lineDataSet.fillColor = android.R.color.darker_gray
        lineDataSet.fillAlpha = android.R.color.holo_red_light

        lineDataSet.formLineWidth =1f
        lineDataSet.setFormLineDashEffect(DashPathEffect(floatArrayOf(10f, 5f), 0f))
        line_chart.xAxis.labelRotationAngle = 0f

        line_chart.data = data
        line_chart.axisRight.isEnabled = false
        line_chart.xAxis.axisMaximum = lineDataSet.entryCount+0.1f
        line_chart.setTouchEnabled(true)
        line_chart.setPinchZoom(true)
        blockchainTransactionsResponse?.let {
            line_chart.description.text = it.description
        }
        line_chart.setNoDataText("No data value!")
        line_chart.animateX(1800, Easing.EaseInExpo)
        val markerView = context?.let { CustomMarker(it, R.layout.entry_point_view) }
        line_chart.marker = markerView
        line_chart.invalidate()
    }

    private fun dataValues(pointValue: List<PointValue>): ArrayList<Entry>{
        entries.clear()
        var index = 0.0
        for (item in pointValue) {
            index += 1
            entries.add(Entry(index.toFloat(), item.y.toFloat()))
        }
        return entries
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        val week = seekBarWeek.progress.toString().plus("weeks")
        val hours = seekBarHours.progress.toString().plus("hours")
        blockchainTransactionViewModel?.searchBlockchainTransactions(week,hours)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) { }

    override fun onStopTrackingTouch(p0: SeekBar?) { }

    override fun updateData(response: Response<BlockchainTransactionsResponse?>?) {
         if (response?.body() != null){
             setupChart(response.body())
         }
    }
}

