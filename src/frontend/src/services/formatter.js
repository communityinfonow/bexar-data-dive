import i18n from '@/i18n'

let formatter = new Intl.NumberFormat();
export function format(type, value) {
  if (isNaN(value) || value === null) {
    return i18n.t('data.no_data');
  }

  let formattedValue = '';
	switch (type) {
      case '3': // percent
        formattedValue = formatter.format(value) + "%";
        break;
      case '4': //currency
        formattedValue = "$" + formatter.format(value);
        break;
      default: // count and rate
        formattedValue = formatter.format(value);
    }

    return formattedValue;
}