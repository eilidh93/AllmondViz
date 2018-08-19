<h1><b> Metagenomic heatmap (name to come)</h1></b>

<font size="5">NameOfHeatmap is a way of displaying viral metagenomic data in heatmap form.<br> It can be used as a way of visualising viral diversity within a sample or between multiple samples.<br><br>

Example:<br>
<img src="example_heatmap.png" alt="[Example Heatmap]"><br>

<br>
NameOfHeatmap is designed to follow on from the following pipeline: <br>

	1. Sequencing of metagenomic sample<br>
	2. Trimming of reads<br>
	3. Alignment of reads against host to remove host reads<br>
	4. Remove bacteria reads with DIAMOND blastx<br>
	5. De novo assembly of reads into contigs<br>
	6. Classification of contigs using DIAMOND blastx<br>
	7. File extended to include taxonID, length, family name and genus name<br></font size="5">
<br>
<b><h2>FEATURES</h2></b>
<font size="5">
	-Display by either Family or Genus <br>
	-Sort by contig length, number of contigs, evalue and percentage of genome size<br>
	-Clustering based on contigs present<br>
	-Different colour schemes<br>
	-View contigs by clicking on elements of the heatmap and extracting the sequences of these contigs by selecting the contig file <br>
	-Option to exlude contigs under a specified length, bacteriophages and anything that does not infect invertebrates<br>
	-Saving the heatmap image<br></font size="5">
<br>
<b><h2>INPUT</h2></b> 

	<font size="5">Input is a tab delimited text file with 16 columns<br></font size="5">

	Contig ID | Seq-ID | % Identical Matches | Alignment Length | No. Mismatches | No. Gap Openings | Start(query) | End(qeury) | Start(subject) | End(subject) | eValue | Bit Score | | Contig Length | Family Name | Genus Name |

