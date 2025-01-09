'''
Script to transform the `postcode.csv` data into a postcode to region mapping.
'''
import csv
import json

def main():
    postcode_map = create_mapping('./postcodes.csv')
    save_map(postcode_map, '../src/main/resources/static/postcode_mapping.json')

    print(f"{len(postcode_map)} postcodes mapped!")


def create_mapping(csv_file_path):
    postcode_map = {}

    with open(csv_file_path, 'r', encoding='utf-8') as csv_file:
        csv_reader = csv.DictReader(csv_file)

        for row in csv_reader:
            postcode = row['POSTLEITZAHL'].strip().strip('"')
            state = row['ISO_3166_1_ALPHA_2_REGION_CODE'].strip().strip('"')
            postcode_map[postcode] = state

    return postcode_map


def save_map(map, file_path):
    with open(file_path, 'w', encoding='utf-8') as json_file:
        json.dump(map, json_file, ensure_ascii=False, indent=2)


if __name__ == "__main__":
    main()
