# JTSplus
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.datasyslab/JTSplus/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.datasyslab/JTSplus)

JTSplus artifacts are hosted in Maven Central. You can add a Maven dependency with the following coordinates:

```
groupId: org.datasyslab
artifactId: JTSplus
version: 0.1.0
```
## Introduction
JTS Topology Suite 1.14 with additional functions for GeoSpark

1. Spatial data partitioning: Add a function in STR-Tree and Quad-Tree to return the leaf nodes bounding envelopes as the boundaries of spatial partitions. 
2. KNN query: Add K Nearest Neighbors in STR-Tree implementation. 

## Functions usage

### Spatial partitioning
STR-Tree partitioning is as follows:

```java
STRtree strtree = new STRtree();
strtree.insert();//Please insert spatial records
...
...
...
strtree.queryBoundary();
```

Quad-Tree partitioning is as follows:

```java
Quadtree quadtree = new Quadtree();
quadtree.insert();//Please insert spatial records
...
...
...
quadtree.queryBoundary();
```
### K Nearest Neighbors (KNN) query

```java
STRtree strtree = new STRtree();
strtree.insert();//Please insert spatial records. Make sure you insert Geometry type object (instead of Envelope or others)
...
...
...
strtree.kNearestNeighbors(new Envelope(-98.6361828, -95.0993852,46.88333326666667,48.392923),fact.toGeometry(new Envelope(-98.6361828, -95.0993852,46.88333326666667,48.392923)),new GeometryItemDistance(), 10);
```


## Example

Please refer to [PartitioningQualityAnalysis](https://github.com/jiayuasu/JTSplus/blob/master/org/datasyslab/jts/utils/PartitioningQualityAnalysis.java) java file for a detailed example.

## Contributor
[Jia Yu](http://www.public.asu.edu/~jiayu2/) (Email: jiayu2@asu.edu)

[Mohamed Sarwat](http://faculty.engineering.asu.edu/sarwat/) (Email: msarwat@asu.edu)
## Acknowledgement

JTSplus makes use of JTS Topology Suite Version 1.14 for some spatial computations.

Please refer [JTS Topology Suite website](http://tsusiatsoftware.net/jts/main.html) for more details.
