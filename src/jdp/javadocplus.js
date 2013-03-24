var jdp = {
	//
	packageMap: function(data) {
		
		var margin = {top: 10, right: 10, bottom: 0, left: 10},
		    width = 960 - margin.left - margin.right,
		    height = 800 - margin.top - margin.bottom;
		
		var border = 6;
		
		var treemap = d3.layout.treemap()
		    .size([width, height])
		    .sticky(true)
		    .padding(border*2)
		    .value(mode());
		
		d3.select("h1").text(data.name);
		d3.select("div.project").remove();

		var div = d3.select("body").append("div")
		    .attr("class", "project border")
		    .style("position", "relative")
		    .style("width", (width) + "px")
		    .style("height", (height) + "px")
		    .style("left", margin.left + "px")
		    .style("top", margin.top + "px");
		
		  var node = div.datum(data).selectAll(".node")
		      .data(treemap.nodes)
		      .enter().append("div")
		      .attr("class", function(d) { return d.type == null ? "node border package" : "node bg "+d.type; })
			  .on("click", function click(d) {
				jdp.packageMap(d.parent);
			  })
		      .call(position);
		
		  d3.selectAll("select").on("change", function change() {
		    node.data(treemap.value(mode()).nodes)
		      .transition()
		      .duration(1000)
		      .call(position);
		});	

		function mode() {
		    var mode = d3.select("select").property("value");
		    return (mode === "count")
		    	? function() { return 1; }
		    	: function(d) { return d[mode]; };
		}	

		function position() {
		  this.each(label);
		  this.style("left", function(d) { 
		  		return d.type == null ? (d.x + border ) + "px" : d.x + "px"; 
		  	})
		      .style("top", function(d) { 
		      	return d.type == null ? (d.y + border) + "px" : d.y + "px"; 
		      })
		      .style("width", function(d) { 
		      	return d.type == null ? Math.max(0, d.dx - border*4 +1) + "px" : Math.max(0, d.dx - 1) + "px"; 
		      })
		      .style("height", function(d) { 
		      	return d.type == null ? Math.max(0, d.dy - border*4 +1) + "px" : Math.max(0, d.dy - 1) + "px"; 
		      });
		}
		
		function label(d) {
			d3.select(this).html("<div class='label'>"+ d.name + "<span>"+d.value+"</span></div>");
		}		
	}
};



