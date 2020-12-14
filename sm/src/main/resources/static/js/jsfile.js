var chartDatastr= decodehtml(chartDatafresh); 
var chartJsonArray= JSON.parse(chartDatastr);

var chartval=chartJsonArray[0];
var arrayLength=chartJsonArray.length;
var numericdata=[];
var labeldata=[];

var totalSum=0;
for(var i=0;i<arrayLength;i++)
{

totalSum+= chartJsonArray[i].value;
 

}

	perc=perc/totalSum*100


for(var i=0;i<arrayLength;i++)
	{
	var perc=chartJsonArray[i].value;

	
	perc= perc/totalSum*100;
	
	numericdata[i]=perc.toFixed(2);
	
	labeldata[i]=chartJsonArray[i].label;
	
	}

new Chart(document.getElementById("MyPieChart"), {
    type: 'doughnut',
    
    // The data for our dataset
    data: {
        labels: labeldata,
        datasets: [{
            label: 'My First dataset',
            backgroundColor: ["#003F5C", "#58508D", "#BC5090" ,"#FF6361","#FFA600","#003F5C", "#58508D", "#BC5090" ,"#FF6361","#FFA600","#003F5C", "#58508D", "#BC5090" ,"#FF6361","#FFA600"],
            data: numericdata
        }]
    },
    
    // Configuration options go here
    options: {
    	title: {
    	display:true,
    	text:'Time Used In A Day Chart (Shown in %)',
      	}
    }	
});
 

function decodehtml(html)
{
	 
	 var txt=document.createElement("textarea");
	 txt.innerHTML=html;
	 return txt.value;
}