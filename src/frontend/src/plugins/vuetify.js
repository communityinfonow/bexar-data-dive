import Vue from 'vue'
import Vuetify from 'vuetify/lib/framework'

Vue.use(Vuetify)

export default new Vuetify({
	theme: {
		options: { customProperties: true },
		themes: {
			light: {
				primary: '#231f20',
				secondary: '#1C54A5',
				light: '#FFFFFF',
				link: '#B8237E',
				blue: '#3b5a98',
				yellow: '#f6921e',
				green: '#3aa38f',
				red: '#b8237e',
				grey: '#EFEFEF'
			}
		}
	}
})
