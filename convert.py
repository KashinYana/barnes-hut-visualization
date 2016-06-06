import random

FI = open('graph.txt', 'r')
FO = open('graph_c.txt', 'w')


nodes = set()
edges = []

transform = {}

for line in FI:
	if line:
		v, w = map(int, line.strip().split())
		nodes.add(v)
		nodes.add(w)
		edges.append([v, w])

id = 0
for v in nodes:
	transform[v] = id
	id = id + 1

FO.write(str(len(nodes)) + " " + str(len(edges)) + '\n')

random.shuffle(edges)
for i in range(len(edges)):
	FO.write(str(transform[edges[i][0]]) + " " + str(transform[edges[i][1]]) + '\n')


FI.close()
FO.close()
