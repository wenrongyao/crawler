/**
 * phantomjs的执行文件
 */
system = require('system')
address = system.args[1];
var page = require('webpage').create();
var url = address;
page.open(url, function(status) {
	//Page is loaded!  
	if (status !== 'success') {
		console.log('Unable to post!');
	} else {
		console.log(page.content);
		phantom.exit();
	}
});