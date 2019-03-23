package org.liquid.scheduler.core.dags;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.ir.ObjectNode;
import jdk.nashorn.internal.runtime.regexp.joni.constants.NodeStatus;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * {@code Dag} 中的节点，代表作业。
 *
 * @author linckye 2018-07-16
 */
public interface Node {

    Node downNode(Node node);

    NodeStatus nodeStatus();



}
