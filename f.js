var gamers = Ext.create('Ext.data.Store', {
    fields: ['id', 'name'],
    data : [
        {"id":"12", "name":"Пупкин Ктото Ктотович"},
    ]
});

var commands = Ext.create('Ext.data.Store', {
    fields: ['id', 'command'],
    data : [
        {"id":"12", "command":"Грузия - армения"},
    ]
});

var panelInsertForecast = Ext.create('Ext.form.Panel', {
		title: 'Внести прогноз',
		region:'north',
		id:'panelInsertForecast',
		layout: 'hbox',
		items:[
			Ext.create('Ext.form.ComboBox', {
				fieldLabel: 'За кого внести',
				store: gamers,
				queryMode: 'local',
				displayField: 'name',
				valueField: 'abbr',
			}),

			Ext.create('Ext.form.ComboBox', {
				fieldLabel: 'На какую игру',
				store: commands,
				queryMode: 'local',
				displayField: 'command',
				valueField: 'id',
			}),

			 {
					xtype: 'label',
					forId: 'myFieldId',
					text: 'Счет',
					margin: '0 0 0 10'
			},
			{
				xtype: 'textfield',
				name: 'oneCount',
				allowBlank: false  // requires a non-empty value
			},
			{
				xtype: 'textfield',
				name: 'twoCount',
				allowBlank: false  // requires a non-empty value
			},
			{
				xtype: 'button',
				text: 'Внести',
			}

		]

	}
);

Ext.create('Ext.data.Store', {
    storeId:'resultStore',
    fields:['gameDate', 'commands', 'gamer', 'result', 'gamerForecast', 'score'],
    data:{'items':[
        {
        	'gameDate': '22.06.2018',
        	"commands":"Грузия - армения",
        	"gamer":"Пупкин Ктото Ктотович",
        	"result":"2:1",
        	"gamerForecast":"2:1",
        	"score":"1"
		}
    ]},
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});


var panelGameResults = Ext.create('Ext.grid.Panel', {
		title: 'Результаты по играм',
		region:'center',
		id:'panelGameResults',
		store: Ext.data.StoreManager.lookup('resultStore'),
		columns: [
			{ text: 'Дата игры',  dataIndex: 'gameDate' , flex: 1},
			{ text: 'Играющие команды', dataIndex: 'commands', flex: 1 },
			{ text: 'Игрок', dataIndex: 'gamer' , flex: 1},
			{ text: 'Итоговый счет', dataIndex: 'result' , flex: 1},
			{ text: 'Прогноз игрока', dataIndex: 'gamerForecast' , flex: 1},
			{ text: 'Очки', dataIndex: 'score' , flex: 1}
		],
	}
);


Ext.create('Ext.data.Store', {
    storeId:'resultFinalStore',
    fields:['gamer', 'winner', 'score', 'plusFive', 'finalScore'],
    data:{'items':[
        {
        	'gamer': 'Пупкин Ктото Ктотович',
        	"winner":"Грузия - армения",
        	"score":"2:1",
        	"plusFive":"1",
        	"finalScore":"12"
		}
    ]},
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});

var panelFinalResults = Ext.create('Ext.grid.Panel', {
		title: 'Итоговые результаты',
		id:'panelFinalResults',
		region: 'center',
		store: Ext.data.StoreManager.lookup('resultFinalStore'),
		columns: [
			{ text: 'Игрок',  dataIndex: 'gamer' , flex: 1},
			{ text: 'Чемпион', dataIndex: 'winner', flex: 1 },
			{ text: 'Очки', dataIndex: 'score' , flex: 1},
			{ text: '+5 за чемпиона', dataIndex: 'plusFive' , flex: 1},
			{ text: 'Итоговые очки', dataIndex: 'finalScore' , flex: 1}
		],
});

Ext.create('Ext.data.Store', {
    storeId:'forecastStore',
    fields:['gameDate', 'commands', 'forecast'],
    data:{'items':[
        {
        	'gameDate': '30.06.2018',
        	"commands":"Грузия - армения",
        	"forecast":"2:1",
		}
    ]},
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});

var panelForecast = Ext.create('Ext.grid.Panel', {
		title: 'Прогноз',
		id:'panelForecast',
		region: 'east',
		width: '50%',
		split: true,
		store: Ext.data.StoreManager.lookup('forecastStore'),
		columns: [
			{ text: 'Дата игры',  dataIndex: 'gameDate' , flex: 1},
			{ text: 'Играющие команды', dataIndex: 'commands', flex: 1 },
			{ text: 'Прогноз игрока', dataIndex: 'forecast' , flex: 1}
		],
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
