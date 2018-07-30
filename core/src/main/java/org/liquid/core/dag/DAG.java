package org.liquid.core.dag;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 有向无环图
 *
 * @author linckye 2018-07-22
 */
@ToString
public class DAG {

    private MutableValueGraph<Node, Edge> graph = ValueGraphBuilder.directed().build();

    public Set<Node> nodes() {
        return graph.nodes();
    }

    public boolean node(Node node) {
        return graph.addNode(node);
    }

    public Edge edge(Node predecessor,
                     Node successor,
                     Edge edge) {
        return graph.putEdgeValue(predecessor, successor, edge);
    }

    public Set<Node> successors(Node node) {
        return graph.successors(node);
    }

    public Set<Node> predecessors(Node node) {
        return graph.predecessors(node);
    }

    public Optional<Edge> edge(Node predecessor,
                               Node successor) {
        return graph.edgeValue(predecessor, successor);
    }

    public int inDegree(Node node) {
        return graph.inDegree(node);
    }

    public int outDegree(Node node) {
        return graph.outDegree(node);
    }

    public Set<Node> startNodes() {
        return graph.nodes().stream()
                .filter(node -> graph.inDegree(node) == 0)
                .collect(Collectors.toSet());
    }

    public Set<Node> endNotes() {
        return graph.nodes().stream()
                .filter(node -> graph.outDegree(node) == 0)
                .collect(Collectors.toSet());
    }

}
