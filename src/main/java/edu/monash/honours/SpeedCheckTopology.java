package edu.monash.honours;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import edu.monash.honours.bolt.SpeedCheckBolt;
import edu.monash.honours.spout.SpeedOutputSpout;

public class SpeedCheckTopology
{
  public static void main(String[] args) throws Exception
  {
    TopologyBuilder builder = new TopologyBuilder();

    builder.setSpout("speed", new SpeedOutputSpout());
    builder.setBolt("speed-check", new SpeedCheckBolt()).shuffleGrouping("speed");

    Config conf = new Config();
    conf.setDebug(true);

    LocalCluster cluster = new LocalCluster();
    cluster.submitTopology("speed-checker", conf, builder.createTopology());
    Utils.sleep(100000);
    cluster.killTopology("speed-checker");
    cluster.shutdown();
  }
}