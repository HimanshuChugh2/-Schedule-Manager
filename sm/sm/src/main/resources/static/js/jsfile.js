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

	
	
	var ctx = document.getElementById("MyPieChart").getContext("2d");
    var myChart = new Chart(ctx, {
    	type: 'bar',
        
        // The data for our dataset
        data: {
            labels: labeldata,
           
            
            
            datasets: [{
            	
            	label:'Time Used In A Day Chart (Shown in %)',
                
                data: numericdata,
                backgroundColor: ["#1E90FF", "#008080", "#00FF7F" ,"#FF00FF","#FFFF00","#FFA500", "#FF1493", "#FF0000" ,"#00FFFF","#008080","#FFE082", "#FFD54F", "#FFCA28" ,"#FFC107","#FFB300"],
 
                
                borderWidth : 1 

      
            }]
        },
        
        // Configuration options go here
        options: {
        	
        	

        
           	responsive: true,
        	maintainAspectRatio:false,
  
      	  title: {
              display: true,
           }
          	 
        }
    });
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*var chart=new Chart(document.getElementById("MyPieChart"), {
    type: 'pie',
    
    // The data for our dataset
    data: {
        labels: labeldata,
        datasets: [{
            label: 'My First dataset',
            backgroundColor: ["#F57F17", "#F9A825", "#FBC02D" ,"#FDD835","#FFEB3B","#FFEE58", "#FFF176", "#FFF59D" ,"#FFF9C4","#FFFDE7","#FFE082", "#FFD54F", "#FFCA28" ,"#FFC107","#FFB300"],
            data: numericdata
        }]
    },
    
    // Configuration options go here
    options: {
    	 
    	display:true,
    	text:'Time Used In A Day Chart (Shown in %)',
    	responsive: true,
      	
      	 
    }	
});*/
 
 
function decodehtml(html)
{
	 
	 var txt=document.createElement("textarea");
	 txt.innerHTML=html;
	 return txt.value;
}



