let formatter = new Intl.NumberFormat();
export function format(type, value) {
  if (isNaN(value)) {
    return 'No Data';
  }

  let formattedValue = '';
	switch (type) {
      case 3: // percent
        formattedValue = formatter.format(value) + "%";
        break;
      case 4: //currency
        formattedValue = "$" + formatter.format(value);
        break;
      default: // count and rate
        formattedValue = formatter.format(value);
    }

    return formattedValue;
}