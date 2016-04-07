# JTSplus
## Introduction
JTS Topology Suite 1.14 with additional functions for GeoSpark

GeoSpark supports spatial data partitioning after loading spatial data into memory. 

Spatial data partitioning improves rang query, join query and KNN query through reducing unnecessary data shuffle across the cluster.

Now supported spatial data partitioning techniques include: Load balanced grids, STR-Tree (in JTSplus) and Quad-Tree (in JTSplus).
## Functions
STR-Tree and Quad-Tree spatial data partitioning methods are built on top of JTS STR-Tree and Quad-Tree implementation.

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

These functions return a list of boundaries which are parts of the entire spatial space.

## Example

Please refer to [PartitioningQualityAnalysis](https://github.com/jiayuasu/JTSplus/blob/master/org/datasyslab/jts/utils/PartitioningQualityAnalysis.java) java file for a detailed example.

## Contributor
[Jia Yu](http://www.public.asu.edu/~jiayu2/) (Email: jiayu2@asu.edu)

## Acknowledgement

JTSplus makes use of JTS Topology Suite Version 1.14 for some spatial computations.

Please refer [JTS Topology Suite website](http://tsusiatsoftware.net/jts/main.html) for more details.
