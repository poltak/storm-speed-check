package edu.monash.honours.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;

import java.util.Map;

public class SpeedOutputSpout extends BaseRichSpout
{
  public void declareOutputFields(final OutputFieldsDeclarer outputFieldsDeclarer)
  {

  }

  public void open(final Map map, final TopologyContext topologyContext,
                   final SpoutOutputCollector spoutOutputCollector)
  {

  }

  public void nextTuple()
  {

  }
}
