package com.rdm.blockchainchart

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.rdm.blockchainchart.model.BlockchainTransactionsResponse
import com.rdm.blockchainchart.viewmodels.BlockchainTransactionViewModel
import kotlinx.android.synthetic.main.chart_view.*
import java.util.*


class ChartFragment : Fragment(), OnChartValueSelectedListener {
    private var blockchainTransactionViewModel: BlockchainTransactionViewModel? = null
    private val entries: ArrayList<Entry> =
        ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
      return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        blockchainTransactionViewModel = ViewModelProviders.of(this).get(BlockchainTransactionViewModel::class.java)
        //timespan=5weeks&rollingAverage=8hours
        blockchainTransactionViewModel?.searchBlockchainTransactions("5weeks","8hours")



        var lineDataSet = LineDataSet(dataValues(), "Periodo")
        var dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(lineDataSet)

        var data = LineData(dataSets)


        lineDataSet.setDrawValues(false)
        lineDataSet.setDrawFilled(true)
        lineDataSet.lineWidth = 3f
        lineDataSet.fillColor = android.R.color.darker_gray
        lineDataSet.fillAlpha = android.R.color.holo_red_light

        line_chart.xAxis.labelRotationAngle = 0f

        line_chart.data = data
        line_chart.axisRight.isEnabled = false
        line_chart.xAxis.axisMaximum = lineDataSet.entryCount+0.1f

        line_chart.setTouchEnabled(true)
        line_chart.setPinchZoom(true)
        line_chart.description.text = "Days"
        line_chart.setNoDataText("No forex yet!")


        line_chart.animateX(1800, Easing.EaseInExpo)

        val markerView = context?.let { CustomMarker(it, R.layout.entry_point_view) }
        line_chart.marker = markerView
        line_chart.invalidate()

       val blockchainTransactionsResponse = blockchainTransactionViewModel?.getBlockchainTransactionsResponseLiveData()
   

    }

    private fun dataValues(): ArrayList<Entry>{

        entries.add(Entry(0f, 20f))
        entries.add(Entry(1f, 20f))
        entries.add(Entry(2f, 20f))
        entries.add(Entry(3f, 5f))
        entries.add(Entry(4f, 16f))
        entries.add(Entry(5f, 8f))
        entries.add(Entry(6f, 10f))
        return entries

    }


    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.i("Entry selected", e.toString());
        Log.i("LOW HIGH", "low: " + line_chart.getLowestVisibleX() + ", high: " + line_chart.getHighestVisibleX());
        Log.i("MIN MAX", "xMin: " + line_chart.getXChartMin() + ", xMax: " + line_chart.getXChartMax() + ", yMin: " + line_chart.getYChartMin() + ", yMax: " + line_chart.getYChartMax());
    }
    override fun onNothingSelected() {

    }
}

