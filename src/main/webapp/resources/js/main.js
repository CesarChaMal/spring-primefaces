function Main(id_list)
{	
	if(typeof System == undefined)

	this.object = null
	this.message = "";
	
	this.test1 = testing1;
	this.test2 = testing2;
}

function testing1(message)
{	
	this.message += 'Hello ' + message;
}

function testing2()
{	
	alert(this.message);
}
