package edu.monash.honours.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

import java.util.Map;

public class SpeedCheckBolt extends BaseRichBolt
{
  public void prepare(final Map map, final TopologyContext topologyContext, final OutputCollector outputCollector)
  {

  }

  public void execute(final Tuple tuple)
  {

  }

  public void declareOutputFields(final OutputFieldsDeclarer outputFieldsDeclarer)
  {

  }
}
