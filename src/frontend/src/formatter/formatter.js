let formatter = new Intl.NumberFormat();
export function format(type, value) {
	switch (type) {
        case "Percent":
        case 3:
          return formatter.format(value) + "%";
        case "Currency":
        case 4:
          return "$" + formatter.format(value);
        default:
          return formatter.format(value);
      }
}