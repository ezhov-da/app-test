var panelInsertForecast = Ext.create('Ext.panel.Panel', {
		title: 'Внести прогноз',
		region:'north',
		id:'panelInsertForecast',
		items:[
			{
				xtype: 'button',
				text: 'Тест Внести прогноз'
			}
		]

	}
);
var panelGameResults = Ext.create('Ext.panel.Panel', {
		title: 'Результаты по играм',
		region:'center',
		id:'panelGameResults',
		items:[
			{
				xtype: 'button',
				text: 'Тест Результаты по играм'
			}
		]
	}
);
var panelFinalResults = Ext.create('Ext.panel.Panel', {
		title: 'Итоговые результаты',
		id:'panelFinalResults',
		region: 'center',
		items:[
			{
				xtype: 'button',
				text: 'Тест Итоговые результаты'
			}
		]
});
var panelForecast = Ext.create('Ext.panel.Panel', {
		title: 'Прогноз',
		id:'panelForecast',
		region: 'east',
		width: '50%',
		split: true,
		items:[
			{
				xtype: 'button',
				text: 'Тест Прогноз'
			}
		]
});


var panelGamerInformationCenter = Ext.create('Ext.panel.Panel', {
	xtype: 'panel',
	region: 'center',
	layout:'border',
	items:[
		panelInsertForecast,
		panelGameResults,
	]
});

var panelGamerInformationEast = Ext.create('Ext.panel.Panel', {
	xtype: 'panel',
	region: 'south',
	layout:'border',
	height: '40%',
	split: true,
	items:[
		panelFinalResults,
		panelForecast,
	]
});

var panelGamerInformation = Ext.create('Ext.panel.Panel', {
		title: 'Информация для участника',
		layout: 'border',
		items:[
			panelGamerInformationCenter,
			panelGamerInformationEast
		]
});



var panelInsertResultGame = Ext.create('Ext.panel.Panel', {});

var basicTabPanel =
	Ext.create('Ext.tab.Panel', {
		title: 'Футбол',
		items: [
		panelGamerInformation
			, {
				title: 'Форма для внесения результата по игре',
				items: [panelInsertResultGame]
			}
		]
	});


Ext.Ajax.useDefaultXhrHeader = false;

Ext.application({
    name: 'Футбол',
    launch: function() {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            items: [basicTabPanel]
        });
    }
});
